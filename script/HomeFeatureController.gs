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
  response = processFeatureForSpecificDevice(deviceID, response)

  request.response = response
  return request.responseWithJson()
}

let processFeatureForSpecificDevice = (deviceID, originalFeature) => {
  let isAccess = getSheetDataAsJson(SHEET_ID, ACCESS_DEVICE_SHEET).some(value => value.device_id == deviceID)
  let feature = {}
  feature[`Features`] = originalFeature
  if(!isAccess) return feature

  feature[`Special For You`] = getSheetDataAsJson(SHEET_ID, SPECIAL_FEATURE_SHEET)
  return feature
}
