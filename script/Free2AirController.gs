let free2AirController = (request) => {
  const f2A = getSheetDataAsJson(SHEET_ID, FREE_2_AIR_SHEET)

  request.response = {
    'Free 2 Air Channels' : f2A
  }
  return request.responseWithJson()
}
