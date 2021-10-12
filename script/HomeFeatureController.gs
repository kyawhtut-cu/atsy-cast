let homeFeatureController = (request) => {
  const payload = request.parameter.payload

  const deviceID = payload.device_id
  const devicePassword = payload.device_password

  if (deviceID == null || devicePassword == null || deviceID == `` || devicePassword == ``) {
    request.status = BAD_REQUEST
    request.message = BAD_REQUEST_MESSAGE
    request.response = []
    return request.responseWithJson()
  }

  let requestUser = checkUserDevice(deviceID, devicePassword)
  if (requestUser == null) {
    request.status = UNAUTHORIZED_REQUEST
    request.message = UNAUTHORIZED_REQUEST_MESSAGE
    request.response = []
    return request.responseWithJson()
  }

  let response = getSheetDataAsJson(SHEET_ID, HOME_FEATURE_SHEET)
  response = processFeatureForSpecificDevice(deviceID, response)

  request.response = response
  return request.responseWithJson()
}

let homeFeatureDetailController = (request) => {
  const payload = request.parameter.payload

  const featureID = payload.feature_id
  if (featureID == null || featureID == ``) {
    request.status = BAD_REQUEST
    request.message = BAD_REQUEST_MESSAGE
    return request.responseWithJson()
  }

  let data = getSheetDataAsJson(SHEET_ID, HOME_FEATURE_SHEET).filter(feature => feature.feature_id == featureID).pop()
  if (data == null) {
    data = getSheetDataAsJson(SHEET_ID, SPECIAL_FEATURE_SHEET).filter(feature => feature.feature_id == featureID).pop()
  }

  request.response = data
  return request.responseWithJson()
}

let processFeatureForSpecificDevice = (deviceID, originalFeature) => {
  let isAccess = getSheetDataAsJson(SHEET_ID, ACCESS_DEVICE_SHEET).some(value => value.device_id == deviceID)
  let feature = {}
  feature[`Features`] = originalFeature
  if (!isAccess) return feature

  feature[`Special For You`] = getSheetDataAsJson(SHEET_ID, SPECIAL_FEATURE_SHEET)
  return feature
}
