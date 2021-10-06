let msysFootballController = (request) => {
  const resultData = {}
  const page = 0
  const apiRequest = MSYS_API_KEY.map(value => {
    return {
      url: `http://msubys.xyz/api/channel/by/filtres/${value.key}/0/${page}/4F5A9C3D9A86FA54EACEDDD635185/d506abfd-9fe2-4b71-b979-feff21bcad13/`,
      method: `get`,
      data: value,
    }
  })

  const apiResponse = UrlFetchApp.fetchAll(apiRequest);
  apiResponse.map(value => renderMsysDataToResponse(JSON.parse(value))).filter(value => value.length > 0).forEach((value, index) => {
    const title = apiRequest[index].data.title
    if (value != null) {
      resultData[title] = value
    }
  })

  request.response = resultData
  return request.responseWithJson()

}

let renderMsysDataToResponse = (data) => {
  const result = []
  data.forEach((channel, index) => {
    if (channel.sources.length != 0) {
      if (channel.sources.some(c => c.type == `m3u8`)) {
        const stream = channel.sources.filter(c => c.type == `m3u8`)[0].url;
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
