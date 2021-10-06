let myCinemaControllerV2 = (request) => {
  const payload = request.parameter.payload

  const subRoute = payload.sub_route
  if (subRoute == null || subRoute == ``) {
    request.status = BAD_REQUEST
    request.message = BAD_REQUEST_MESSAGE
    return request.responseWithJson()
  }

  if (subRoute == `category`) {
    return myCinemaCategoryControllerV2(request)
  } else if (subRoute == `videoListByCategoryID`) {
    return myCinemaVideoListByCategoryIDControllerV2(request)
  } else if (subRoute == `videoDetail`) {
    return myCinemaVideoDetailControllerV2(request)
  } else if (subRoute == `search`) {
    return searchMyCinemaVideoControllerV2(request)
  } else {
    request.status = NOT_FOUND
    request.message = ROUTE_NOT_FOUND
    return request.responseWithJson()
  }
}

let myCinemaCategoryControllerV2 = (request) => {
  const response = UrlFetchApp.fetch(`${MYCINEMA_BASE_URL}data?channel=app&language=ENG&action=category&type=1`).getContentText()
  const categoryResponse = JSON.parse(response).map((category) => {
    let data = {}
    data[`category_id`] = category.category_id
    data[`category_title`] = category.name
    return data
  })
  request.response = categoryResponse
  return request.responseWithJson()
}

let myCinemaVideoListByCategoryIDControllerV2 = (request) => {
  const payload = request.parameter.payload

  const categoryID = payload.category_id
  const page = payload.page

  if (categoryID == null || page == null) {
    request.status = BAD_REQUEST
    request.message = BAD_REQUEST_MESSAGE
    return request.responseWithJson()
  }

  request.response = fetchVideoList(`${MYCINEMA_BASE_URL}data?channel=app&language=ENG&action=videoBycategory&categoryId=${categoryID}&page=${page}`)
  return request.responseWithJson()
}

let myCinemaVideoDetailControllerV2 = (request) => {
  const payload = request.parameter.payload

  const videoID = payload.video_id
  if (videoID == null || videoID == ``) {
    request.status = BAD_REQUEST
    request.message = BAD_REQUEST_MESSAGE
    return request.responseWithJson()
  }

  let response = UrlFetchApp.fetch(`${MYCINEMA_BASE_URL}data?channel=app&language=MYA&action=film&episodes=1&videoId=${videoID}`).getContentText()
  response = JSON.parse(response)

  let videoEpisodeSource = []
  let videoSource = []

  if(!response.isSeries) {
    videoSource = processVideoSource(videoID, 1)
  } else if(response.isSeries) {
    for(let episodeNo = 1; episodeNo <= response.numberVideo; episodeNo++) {
      videoEpisodeSource.push({
        episode_id: videoID + `` + episodeNo,
        episode_title: `Episode ${episodeNo}`,
        episode_source: processVideoSource(videoID, episodeNo)
      })
    }
  }

  const videoDetail = {
    video_id: videoID,
    video_title: response.title,
    video_cover: response.banner.split(`;`)[1],
    video_description: response.content,
    video_source: videoSource,
    video_episode_source: videoEpisodeSource,
    video_related: fetchVideoList(`${MYCINEMA_BASE_URL}data?channel=app&language=ENG&action=trending`)
  }

  request.response = videoDetail

  return request.responseWithJson()
}

let searchMyCinemaVideoControllerV2 = (request) => {
  const payload = request.parameter.payload

  const query = payload.query
  if (query == null || query == ``) {
    request.status = BAD_REQUEST
    request.message = BAD_REQUEST_MESSAGE
    return request.responseWithJson()
  }

  request.response = fetchVideoList(`${MYCINEMA_BASE_URL}data?channel=app&language=ENG&action=search&name=${query}`)
  return request.responseWithJson()
}

let processVideoSource = (videoID, episodeNo) => {

  const response = UrlFetchApp.fetch(`${MYCINEMA_BASE_URL}data?channel=app&language=ENG&action=film&episodes=${episodeNo}&videoId=${videoID}`).getContentText()

  const videoData = JSON.parse(response)

  return [
    {
      quality: videoData.resolution,
      url: `https://***/media/${videoData.source}`
    }
  ]
}

let fetchVideoList = (url) => {
  const response = UrlFetchApp.fetch(url).getContentText()
  const videoList = JSON.parse(response).map((c) => {

    let data = {
      video_id: c.videoId,
      video_title: c.title,
      video_poster: c.avatar,
      video_cover: c.banner.split(`;`)[1],
      video_view_count: `View - ${c.viewNumber}`,
      video_episode: ``
    }

    if(c.isSeries) {
      data.video_episode = `Eps - ${c.numberVideo}/${c.episodesTotal}`
    }

    return data
  })

  return videoList
}
