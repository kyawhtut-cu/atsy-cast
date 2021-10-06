let category = [
  {
    category_id: 5,
    category_title: `Romantic`,
  },
  {
    category_id: 17,
    category_title: `Adventure`,
  },
  {
    category_id: 2,
    category_title: `Cartoon`,
  },
  {
    category_id: 14,
    category_title: `Comendy`,
  },
  {
    category_id: 11,
    category_title: `Horror`,
  },
  {
    category_id: 20,
    category_title: `Action`,
  },
  {
    category_id: 8,
    category_title: `Detective`,
  },
  {
    category_id: 141,
    category_title: `Video`,
  }
]

let myCinemaController = (request) => {
  let parameter = request.parameter

  let subRoute = parameter.sub_route
  if (subRoute == null || subRoute == ``) {
    request.status = BAD_REQUEST
    request.message = BAD_REQUEST_MESSAGE
    return request.responseWithJson()
  }

  if (subRoute == `category`) {
    return myCinemaCategoryController(request)
  } else if (subRoute == `videoListByCategoryID`) {
    return myCinemaVideoListByCategoryIDController(request)
  } else if (subRoute == `videoDetail`) {
    return myCinemaVideoDetailController(request)
  } else if (subRoute == `search`) {
    return searchMyCinemaVideoController(request)
  } else {
    request.status = NOT_FOUND
    request.message = ROUTE_NOT_FOUND
    return request.responseWithJson()
  }
}

let myCinemaCategoryController = (request) => {
  request.response = category
  return request.responseWithJson()
}

let myCinemaVideoListByCategoryIDController = (request) => {
  const parameter = request.parameter
  const categoryID = parameter.category_id
  const page = parameter.page
  if (categoryID == null || categoryID == `` || page == null || page == ``) {
    request.status = BAD_REQUEST
    request.message = BAD_REQUEST_MESSAGE
    return request.responseWithJson()
  }

  const content = UrlFetchApp.fetch(`https://***/category/${categoryID}?page=${page}`).getContentText()
  const result = parseMyCinemaVideo(content)

  request.response = result
  return request.responseWithJson()
}

let myCinemaVideoDetailController = (request) => {
  const videoID = request.parameter.video_id
  if (videoID == null || videoID == ``) {
    request.status = BAD_REQUEST
    request.message = BAD_REQUEST_MESSAGE
    return request.responseWithJson()
  }

  const content = UrlFetchApp.fetch(`https://***/video/${videoID}`).getContentText()
  const $ = Cheerio.load(content)
  const videoTitle = $(`span[class^=vi-name]`).text()
  const videoCover = $(`video[id^=my_video_chua_dang_ky]`).attr(`poster`)
  const videoDescription = $(`div[class^=more]`).text()

  const videoList = $(`div[class^=list-episode] a`)
  const videoCount = videoList.length
  let videoSource = parseVideoSource(videoID, 1)
  const videoEpisodeSource = []
  if (videoCount > 1) {
    videoSource = []
    videoList.each((index, element) => {
      const episode = parseVideoSource(videoID, index + 1)
      videoEpisodeSource.push({
        episode_id: videoID + `` + (index + 1),
        episode_title: `Episode ${index + 1}`,
        episode_source: episode
      })
    })
  }

  const videoRelated = parseMyCinemaVideo(content)

  request.response = {
    video_id: videoID,
    video_title: videoTitle,
    video_cover: videoCover,
    video_description: videoDescription.replaceAll(`\t`, ``),
    video_source: videoSource,
    video_episode_source: videoEpisodeSource,
    video_related: videoRelated
  }
  return request.responseWithJson()
}

let searchMyCinemaVideoController = (request) => {
  const query = request.parameter.query
  if (query == null || query == ``) {
    request.status = BAD_REQUEST
    request.message = BAD_REQUEST_MESSAGE
    return request.responseWithJson()
  }
  const response = UrlFetchApp.fetch(`https://***/search.html?name=${query}`).getContentText()
  const searchResponse = []
  JSON.parse(response).forEach((value) => {
    searchResponse.push({
      video_id: value.videoId,
      video_title: value.title,
      video_poster: value.avatar,
      video_view_count: value.viewNumber,
      video_episode: `Eps${value.numberVideo}/${value.episodesTotal}`
    })
  })
  request.response = searchResponse
  return request.responseWithJson()
}

let parseMyCinemaVideo = (content) => {
  const $ = Cheerio.load(content)
  let result = []
  $(`div .film-item`).each(function (index, element) {
    let videoID = $(element).find(`a`).attr(`href`).trim().split(`/`).pop()
    let videoViewCount = $(element).find(`span .view`).text()
    let videoTitle = $(element).find(`span[class^=film-name]`).text().trim().split(videoViewCount)[0].trim()
    let videoPoster = $(element).find(`img`).attr(`src`)
    let videoEpisode = $(element).find(`span[class^=episode]`).text()
    result.push({
      video_id: videoID,
      video_title: videoTitle,
      video_poster: videoPoster,
      video_view_count: `View - ${videoViewCount}`,
      video_episode: videoEpisode
    })
  })
  return result
}

let parseVideoSource = (videoID, episodeID) => {
  const content = UrlFetchApp.fetch(`https://***/video/${videoID}?eps=${episodeID}`).getContentText()
  const $ = Cheerio.load(content)

  let videoURL = []
  let videoQuality = []
  $('script:not([src])').each((index, script) => {
    let videoData = script.children[0].data
    let isMatch = videoData.match(/source_video = (\[.*?\])/)
    if (isMatch) {
      videoURL = JSON.parse(isMatch[1])
      videoQuality = JSON.parse(videoData.match(/resolution_video = (\[.*?\])/)[1])
      return false
    }
  })

  let videoSource = []
  videoURL.forEach((value, index) => {
    videoSource.push({
      quality: videoQuality[index],
      url: `https://***/media/${value}`
    })
  })
  return videoSource
}
