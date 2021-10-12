let viuController = (request) => {
  const payload = request.parameter.payload

  const subRoute = payload.sub_route

  if (subRoute == null || subRoute == ``) {
    request.status = BAD_REQUEST
    request.message = BAD_REQUEST_MESSAGE
    return request.responseWithJson()
  }

  if (subRoute == `drawerMenu`) {
    return viuDrawerMenu(request)
  } else if (subRoute == `drawerDetail`) {
    return viuDrawerDetail(request)
  } else if (subRoute == `videoListByCategoryID`) {
    return viuVideoListByCategoryID(request)
  } else if (subRoute == `videoEpisodeList`) {
    return viuEpisodeList(request)
  } else if (subRoute == `videoRelatedList`) {
    return viuRelated(request)
  } else if (subRoute == `videoPlaylistList`) {
    return viuPlaylist(request)
  } else if (subRoute == `search`) {
    return viuSearch(request)
  } else {
    request.status = NOT_FOUND
    request.message = ROUTE_NOT_FOUND
    return request.responseWithJson()
  }
}



let fetchDrawerMenuList = () => {
  let response = UrlFetchApp.fetch(`https://****/program/****/****/****/mm/default/my/home.json`).getContentText()

  response = convertDecNCR2Char(response)
  response = JSON.parse(response).container.map((item) => {
    let data = {
      drawer_key: item.id,
      drawer_title: forceConvert(item.title),
      drawer_type: `grid`
    }
    return data
  })
  return response
}

let viuDrawerMenu = (request) => {
  const drawerList = fetchDrawerMenuList()

  request.response = VIU_DRAWER_MENU_LIST
  drawerList.forEach((drawer) => {
    request.response.push(drawer)
  })

  return request.responseWithJson()
}

let viuAccountToken = () => {
  const options = {
    method: POST,
    contentType: APPLICATION_JSON,
    payload: JSON.stringify({
      deviceId: VIU_DEVICE_ID
    }),
    headers: VIU_REQUEST_HEADER,
    muteHttpExceptions: false
  }
  const response = UrlFetchApp.fetch(
    `https://****/user/****?ver=****`,
    options
  ).getContentText()

  const auth = JSON.parse(response)

  return auth.token
}

let viuDrawerDetail = (request) => {
  const payload = request.parameter.payload

  const drawerKey = payload.drawer_key

  if (drawerKey == null) {
    request.status = BAD_REQUEST
    request.message = BAD_REQUEST_MESSAGE
    return request.responseWithJson()
  }
  let response = UrlFetchApp.fetch(VIU_DRAWER_BASE_URL + drawerKey + `.****`).getContentText()

  response = convertDecNCR2Char(response)
  response = JSON.parse(response).container

  let dataList = []
  response.forEach((container) => {
    const videoList = []
    container.item.filter((item) => item.id != ``).forEach((item) => {
      let coverID = item.spotlight_tcid_16x9
      if (coverID == null) {
        coverID = item.tcid_16x9
      }
      let posterID = item.poster_cid
      if (posterID == null) {
        posterID = item.tcid_2x3_t
      }

      let subTitle = []

      if (item.media && item.media.subtitles && item.media.subtitles.subtitle) {
        item.media.subtitles.subtitle.filter((sub) => sub.format == `srt`).forEach((sub) => {
          const lang = VIU_LANGUAGE[sub.language]
          if (lang != null) {
            let key = lang.key
            const srtURL = item[`subtitle_${lang.key}_srt`]
            subTitle.push({
              language: key + `Srt`,
              display_language: lang.title,
              language_url: srtURL
            })
          }
        })
      }

      videoList.push({
        video_id: item.id,
        playlist_id: item.playlistid ? item.playlistid : ``,
        video_title: item.title,
        video_description: item.description ? item.description : ``,
        video_poster: VIU_IMAGE_BASE_URL + `` + posterID,
        video_cover: VIU_IMAGE_BASE_URL + `` + coverID,
        video_url: item.href ? item.href : ``,
        video_subtitles: subTitle,
        video_company_name: item.CP_name ? item.CP_name : item.drm ? item.drm : ``,
        video_content_type: item.contenttype,
        video_episode_total: item.total ? item.total : 0,
      })
    })
    dataList.push({
      id: container.id,
      title: container.title,
      description: container.description,
      video_list: videoList,
    })
  })

  request.response = dataList
  return request.responseWithJson()
}

let viuVideoListByCategoryID = (request) => {
  const payload = request.parameter.payload

  const categoryID = payload.category_id
  const page = payload.page // start 1

  if (categoryID == null || page == null) {
    request.status = BAD_REQUEST
    request.message = BAD_REQUEST_MESSAGE
    return request.responseWithJson()
  }

  const start = (page - 1) * 20

  const authToken = viuAccountToken()

  const options = {
    method: GET,
    contentType: APPLICATION_JSON,
    headers: {
      "authorization": `Bearer ${authToken}`,
    },
    muteHttpExceptions: false
  }
  let response = UrlFetchApp.fetch(
    `${VIU_BASE_URL}****/load?ver=****`,
    options
  ).getContentText()

  response = convertDecNCR2Char(response)
  response = JSON.parse(response).response

  const videoList = []

  if (response.status == `success` && response.container) {
    response.container.item.filter(item => item.id != ``).forEach((item, index) => {
      let coverID = item.spotlight_tcid_16x9
      if (coverID == null) {
        coverID = item.tcid_16x9
      }
      let posterID = item.poster_cid
      if (posterID == null) {
        posterID = item.tcid_2x3_t
      }

      let subTitle = []

      if (item.media && item.media.subtitles && item.media.subtitles.subtitle) {
        item.media.subtitles.subtitle.filter((sub) => sub.format == `srt`).forEach((sub) => {
          const lang = VIU_LANGUAGE[sub.language]
          if (lang != null) {
            let key = lang.key
            const srtURL = item[`subtitle_${lang.key}_srt`]
            subTitle.push({
              language: key + `Srt`,
              display_language: lang.title,
              language_url: srtURL
            })
          }
        })
      }

      videoList.push({
        video_id: item.id,
        playlist_id: item.playlistid ? item.playlistid : ``,
        video_title: item.title,
        video_description: item.description ? item.description : ``,
        video_poster: VIU_IMAGE_BASE_URL + `` + posterID,
        video_cover: VIU_IMAGE_BASE_URL + `` + coverID,
        video_url: item.href ? item.href : ``,
        video_subtitles: subTitle,
        video_company_name: item.CP_name ? item.CP_name : item.drm ? item.drm : ``,
        video_content_type: item.contenttype,
        video_episode_total: item.total ? item.total : 0,
      })
    })
  }

  request.response = videoList
  return request.responseWithJson()
}

let viuPlaylist = (request) => {

  const payload = request.parameter.payload

  const playlistID = payload.playlist_id
  const page = payload.page

  if (playlistID == null || page == null) {
    request.status = BAD_REQUEST
    request.message = BAD_REQUEST_MESSAGE
    return request.responseWithJson()
  }

  const authToken = viuAccountToken()

  const options = {
    method: GET,
    contentType: APPLICATION_JSON,
    headers: {
      "authorization": `Bearer ${authToken}`,
    },
    muteHttpExceptions: false
  }

  let response = UrlFetchApp.fetch(
    `${VIU_BASE_URL}****/load?ver=****`,
    options
  ).getContentText()

  response = convertDecNCR2Char(response)
  response = JSON.parse(response).response

  const playlistList = []
  let playlistTitle = ``
  let playlistTotal = 0

  if (response.status == `success` && response.container) {

    playlistTitle = response.container.title
    playlistTotal = response.container.total

    response.container.item.filter((item) => item.id != ``).forEach((item) => {
      let posterID = item.tcid_2x3_t
      if (posterID == null) {
        posterID = item.poster_cid
      }

      let subTitle = []

      if (item.media && item.media.subtitles && item.media.subtitles.subtitle) {
        item.media.subtitles.subtitle.filter((sub) => sub.format == `srt`).forEach((sub) => {
          const lang = VIU_LANGUAGE[sub.language]
          if (lang != null) {
            let key = lang.key
            const srtURL = item[`subtitle_${lang.key}_srt`]
            subTitle.push({
              language: key + `Srt`,
              display_language: lang.title,
              language_url: srtURL
            })
          }
        })
      }

      playlistList.push({
        video_id: item.id,
        playlist_id: item.playlist_id ? item.playlist_id : ``,
        video_title: item.title,
        video_description: item.description ? item.description : ``,
        video_poster: VIU_IMAGE_BASE_URL + `` + posterID,
        video_cover: VIU_IMAGE_BASE_URL + `` + (item.tcid_16x9 ? item.tcid_16x9 : item.tcid),
        video_url: item.href ? item.href : ``,
        video_subtitles: subTitle,
        video_company_name: item.CP_name ? item.CP_name : item.drm ? item.drm : ``,
        video_content_type: item.contenttype,
        video_episode_total: item.total ? item.total : 0,
      })
    })
  }

  if (playlistTitle != ``) {
    request.response = {
      title: playlistTitle,
      total: playlistTotal,
      playlist_list: playlistList
    }
  }

  return request.responseWithJson()
}

let viuEpisodeList = (request) => {
  const payload = request.parameter.payload

  const videoID = payload.video_id
  const page = payload.page

  if (videoID == null || page == null) {
    request.status = BAD_REQUEST
    request.message = BAD_REQUEST_MESSAGE
    return request.responseWithJson()
  }

  const authToken = viuAccountToken()

  request.response = fetchEpisodeList(authToken, videoID, (page - 1) * 20)
  return request.responseWithJson()
}

let viuRelated = (request) => {

  const payload = request.parameter.payload

  const videoID = payload.video_id

  if (videoID == null) {
    request.status = BAD_REQUEST
    request.message = BAD_REQUEST_MESSAGE
    return request.responseWithJson()
  }

  const authToken = viuAccountToken()

  const options = {
    method: GET,
    contentType: APPLICATION_JSON,
    headers: {
      "authorization": `Bearer ${authToken}`,
    },
    muteHttpExceptions: false
  }

  let response = UrlFetchApp.fetch(
    `${VIU_BASE_URL}search/****?ver=****`,
    options
  ).getContentText()

  response = convertDecNCR2Char(response)
  if (response != ``) {
    response = JSON.parse(response)
  }

  const relatedList = []

  if (response != `` && response.response && response.response.container) {
    response = response.response.container

    if (Array.isArray(response)) {
      const tmp = []
      response.forEach((container) => {
        container.item.filter((item) => item.id != ``).forEach((item) => {
          tmp.push(item)
        })
      })

      response = tmp
    } else {
      response = response.item.filter((item) => item.id != ``)
    }

    response.forEach((item) => {
      let posterID = item.tcid_2x3_t
      if (posterID == null) {
        posterID = item.poster_cid
      }

      let subTitle = []

      if (item.media && item.media.subtitles && item.media.subtitles.subtitle) {
        item.media.subtitles.subtitle.filter((sub) => sub.format == `srt`).forEach((sub) => {
          const lang = VIU_LANGUAGE[sub.language]
          if (lang != null) {
            let key = lang.key
            const srtURL = item[`subtitle_${lang.key}_srt`]
            subTitle.push({
              language: key + `Srt`,
              display_language: lang.title,
              language_url: srtURL
            })
          }
        })
      }

      relatedList.push({
        video_id: item.id,
        playlist_id: item.playlist_id ? item.playlist_id : ``,
        video_title: item.title,
        video_description: item.description ? item.description : ``,
        video_poster: VIU_IMAGE_BASE_URL + `` + posterID,
        video_cover: VIU_IMAGE_BASE_URL + `` + (item.tcid_16x9 ? item.tcid_16x9 : item.tcid),
        video_url: item.href ? item.href : ``,
        video_subtitles: subTitle,
        video_company_name: item.CP_name ? item.CP_name : item.drm ? item.drm : ``,
        video_content_type: item.contenttype,
        video_episode_total: item.total ? item.total : 0,
      })
    })
  }

  request.response = {
    title: `ပုံစံတူ ဇာတ်ကားများ`,
    total: relatedList.length,
    related_list: relatedList
  }

  return request.responseWithJson()
}

let viuSearch = (request) => {
  const payload = request.parameter.payload

  const keyWord = payload.key_word
  if (keyWord == null || keyWord == ``) {
    request.status = BAD_REQUEST
    request.message = BAD_REQUEST_MESSAGE
    return request.responseWithJson()
  }

  const authToken = viuAccountToken()

  const options = {
    method: GET,
    contentType: APPLICATION_JSON,
    headers: {
      "authorization": `Bearer ${authToken}`,
    },
    muteHttpExceptions: false
  }

  let response = UrlFetchApp.fetch(
    `${VIU_BASE_URL}search/****?ver=****`,
    options
  ).getContentText()

  response = convertDecNCR2Char(response)
  response = JSON.parse(response).response

  const searchResponse = []

  if (response.container && response.status == `success`) {
    response.container.forEach((container) => {
      if (Array.isArray(container.item)) {
        const videoList = []
        container.item.filter((item) => item.id != ``).forEach((item) => {
          let posterID = item.tcid_2x3_t
          if (posterID == null) {
            posterID = item.poster_cid
          }

          let subTitle = []

          if (item.media && item.media.subtitles && item.media.subtitles.subtitle) {
            item.media.subtitles.subtitle.filter((sub) => sub.format == `srt`).forEach((sub) => {
              const lang = VIU_LANGUAGE[sub.language]
              if (lang != null) {
                let key = lang.key
                const srtURL = item[`subtitle_${lang.key}_srt`]
                subTitle.push({
                  language: key + `Srt`,
                  display_language: lang.title,
                  language_url: srtURL
                })
              }
            })
          }

          videoList.push({
            video_id: item.id,
            playlist_id: item.playlist_id ? item.playlist_id : ``,
            video_title: item.title,
            video_description: item.description ? item.description : ``,
            video_poster: VIU_IMAGE_BASE_URL + `` + posterID,
            video_cover: VIU_IMAGE_BASE_URL + `` + (item.tcid_16x9 ? item.tcid_16x9 : item.tcid),
            video_url: item.href ? item.href : ``,
            video_subtitles: subTitle,
            video_company_name: item.CP_name ? item.CP_name : item.drm ? item.drm : ``,
            video_content_type: item.contenttype,
            video_episode_total: item.total ? item.total : 0,
          })
        })
        searchResponse.push({
          title: container.title,
          video_list: videoList
        })
      }
    })
  }

  request.response = searchResponse
  return request.responseWithJson()
}

let fetchEpisodeList = (authToken, videoID, startCount) => {
  const options = {
    method: GET,
    contentType: APPLICATION_JSON,
    headers: {
      "authorization": `Bearer ${authToken}`,
    },
    muteHttpExceptions: false
  }

  let response = UrlFetchApp.fetch(
    `${VIU_BASE_URL}****/load?ver=****`,
    options
  ).getContentText()

  response = convertDecNCR2Char(response)
  response = JSON.parse(response).response

  const episodeList = []

  if (response.status == `success` && response.container) {
    response.container.item.filter((item) => item.id != ``).forEach((item) => {
      let coverID = item.tcid_16x9
      if (coverID == null) {
        coverID = item.id
      }

      let subTitle = []

      if (item.media && item.media.subtitles && item.media.subtitles.subtitle) {
        item.media.subtitles.subtitle.filter((sub) => sub.format == `srt`).forEach((sub) => {
          const lang = VIU_LANGUAGE[sub.language]
          if (lang != null) {
            let key = lang.key
            const srtURL = item[`subtitle_${lang.key}_srt`]
            subTitle.push({
              language: key + `Srt`,
              display_language: lang.title,
              language_url: srtURL
            })
          }
        })
      }

      episodeList.push({
        episode_id: item.id,
        episode_title: item.display_title,
        episode_description: item.description ? item.description : ``,
        episode_cover: VIU_IMAGE_BASE_URL + `` + coverID,
        episode_url: item.href,
        episode_subtitle: subTitle
      })
    })
  }

  return episodeList
}
