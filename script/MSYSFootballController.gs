let msysFootballController = (request) => {
  let resultData = {}
  let page = 0
  let apiRequest = MSYS_API_KEY.map(value => {
    return {
      url: `http://***/api/channel/by/filtres/${value.key}/0/${page}/***/***/`,
      method: `get`,
      data: value,
    }
  })

  let apiResponse = UrlFetchApp.fetchAll(apiRequest);
  apiResponse.map(value => renderMsysDataToResponse(JSON.parse(value))).filter(value => value.length > 0).forEach((value, index) => {
    let title = apiRequest[index].data.title
    if (value != null) {
      resultData[title] = value
    }
  })

  request.response = resultData
  return request.responseWithJson()

}

let renderMsysDataToResponse = (data) => {
  let result = []
  data.forEach((channel, index) => {
    if (channel.sources.length != 0) {
      if (channel.sources.some(c => c.type == `m3u8`)) {
        let stream = channel.sources.filter(c => c.type == `m3u8`)[0].url;
        result.push({
          channel_id: channel.id,
          channel_name: channel.title,
          channel_order: index,
          channel_status: true,
          channel_cover: channel.image,
          channel_stream: stream,
          channel_origin: ``,
          channel_referer: ``,
          channel_agent: ``,
        })
      }
    }
  })

  return result
}
