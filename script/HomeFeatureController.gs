let homeFeatureController = (request) => {
  let requestParameter = request.parameter
  if (requestParameter.device_id == null || requestParameter.device_password == null || requestParameter.device_id == `` || requestParameter.device_password == ``) {
    request.status = BAD_REQUEST
    request.message = BAD_REQUEST_MESSAGE
    request.response = []
    return request.responseWithJson()
  }

  let deviceID = requestParameter.device_id
  let devicePassword = requestParameter.device_password

  let requestUser = checkUserDevice(deviceID, devicePassword)
  if (requestUser == null) {
    request.status = UNAUTHORIZED_REQUEST
    request.message = UNAUTHORIZED_REQUEST_MESSAGE
    request.response = []
    return request.responseWithJson()
  }

  let response = getSheetDataAsJson(SHEET_ID, HOME_FEATURE_SHEET)
  request.response = response
  return request.responseWithJson()
}
