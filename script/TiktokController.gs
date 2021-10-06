let tiktokController = (request) => {

  const options = {
    method: POST,
    contentType: APPLICATION_JSON,
    payload: JSON.stringify(TIKTOK_REQUEST_DATA),
    muteHttpExceptions: false
  };
  const itemList = UrlFetchApp.fetch(REQBIN_BASE_URL, options).getContentText()
  let videoList = []

  JSON.parse(JSON.parse(itemList).Content).itemList.forEach((item, index) => {
    videoList.push({
      video_id: item.video.id,
      video_description: item.desc,
      video_cover: item.video.originCover,
      video_dynamic_cover: item.video.dynamicCover,
      video_play_url: item.video.playAddr,
      video_download_url: item.video.downloadAddr,
      video_author: {
        author_id: item.author.id,
        author_user_name: item.author.uniqueId,
        author_name: item.author.nickname,
        author_thumbnail: {
          default_thumbnail: item.author.avatarThumb,
          medium_thumbnail: item.author.avatarMedium,
          large_thumbnail: item.author.avatarLarger,
        }
      },
      video_music: {
        music_id: item.music.id,
        music_title: item.music.title,
        music_play_url: item.music.playUrl,
        music_author: item.music.authorName,
        music_thumbnail: {
          default_thumbnail: item.music.coverThumb,
          medium_thumbnail: item.music.coverMedium,
          large_thumbnail: item.music.coverLarge,
        }
      }
    })
  })

  request.response = videoList
  return request.responseWithJson()
}
