let checkUserDevice = (deviceID, devicePassword) => {
  let userList = getSheetDataAsJson(SHEET_ID, DEVICE_SHEET).filter(user => user.device_id == deviceID && user.device_password == devicePassword)

  if (userList[0] != null) {
    delete userList[0].adult_password
    delete userList[0].device_password
  }

  return userList[0]
}

let checkDevicePasswordController = (request) => {
  let parameter = request.parameter

  let deviceID = parameter.device_id
  let devicePassword = parameter.device_password

  if(deviceID == null || deviceID == `` || devicePassword == null || devicePassword == ``) {
    request.status = BAD_REQUEST
    request.message = BAD_REQUEST_MESSAGE
    return request.responseWithJson()
  }

  let user = checkUserDevice(deviceID, devicePassword)
  if(user == null) {
    request.status = UNAUTHORIZED_REQUEST
    request.message = UNAUTHORIZED_REQUEST_MESSAGE
    return request.responseWithJson()
  }

  request.response = user
  return request.responseWithJson()
}

let checkAdultPasswordController = (request) => {
  let parameter = request.parameter

  let deviceID = parameter.device_id
  let adultPassword = parameter.adult_password

  if(deviceID == null || deviceID == `` || adultPassword == null || adultPassword == ``) {
    request.status = BAD_REQUEST
    request.message = BAD_REQUEST_MESSAGE
    return request.responseWithJson()
  }

  let user = getSheetDataAsJson(SHEET_ID, DEVICE_SHEET).filter(user => user.device_id == deviceID && user.adult_password == adultPassword)
  if (user[0] != null) {
    delete user[0].adult_password
    delete user[0].device_password
  }
  user = user[0]
  if(user == null) {
    request.status = UNAUTHORIZED_REQUEST
    request.message = UNAUTHORIZED_REQUEST_MESSAGE
    return request.responseWithJson()
  }

  request.response = user
  return request.responseWithJson()
}

let registerDeviceController = (request) => {
  let parameter = request.parameter

  let deviceID = parameter.device_id
  let deviceName = parameter.device_name

  if(deviceID == null || deviceID == `` || deviceName == null || deviceName == ``) {
    request.status = BAD_REQUEST
    request.message = BAD_REQUEST_MESSAGE
    return request.responseWithJson()
  }

  let user = getSheetDataAsJson(SHEET_ID, DEVICE_SHEET).filter(user => user.device_id == deviceID)
  if (user[0] != null) {
    delete user[0].adult_password
    delete user[0].device_password
  }
  user = user[0]
  if(user == null) {
    let devicePassword = Math.floor((Math.random() * 999999) + 101)
    let adultPassword = Math.floor((Math.random() * 999999) + 101)

    let deviceSheet = getSheetByName(SHEET_ID, DEVICE_SHEET)
    deviceSheet.appendRow([
      deviceID,
      deviceName,
      deviceName,
      devicePassword,
      adultPassword
    ])

    user = {
      device_id: deviceID,
      display_name: deviceName,
      device_name: deviceName,
    }
  }

  request.response = user
  return request.responseWithJson()
}

let changeDisplayNameController = (request) => {
  let parameter = request.parameter

  let deviceID = parameter.device_id
  let displayName = parameter.display_name

  if(deviceID == null || deviceID == `` || displayName == null || displayName == ``) {
    request.status = BAD_REQUEST
    request.message = BAD_REQUEST_MESSAGE
    return request.responseWithJson()
  }

  let user = getSheetDataAsJson(SHEET_ID, DEVICE_SHEET).filter(user => user.device_id == deviceID)
  if (user[0] != null) {
    delete user[0].adult_password
    delete user[0].device_password
  }
  user = user[0]
  if(user == null) {
    request.status = NOT_FOUND
    request.message = NOT_FOUND_MESSAGE
  } else if(user != null) {

    let deviceSheet = getSheetByName(SHEET_ID, DEVICE_SHEET)
    let rowIndex = findRowIndex(SHEET_ID, DEVICE_SHEET, `device_id`, deviceID)
    let columnIndex = findColumnIndex(SHEET_ID, DEVICE_SHEET, `display_name`)
    deviceSheet.getRange(rowIndex, columnIndex).setValue(displayName)

    user.display_name = displayName
  }

  request.response = user
  return request.responseWithJson()
}
