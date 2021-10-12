function doGet(e) {

  // Instantiate Request
  var request = new Request(e.parameter)

  // Building routes
  var route = new Route()

  route.on(`msubMovies`, msubMoviesController)

  // Register the route with request
  request.register(route)

  return request.process()
}

function doPost(e) {

  // Instantiate PostRequest
  var request = new PostRequest(null)

  if (e.postData && e.postData.contents) {
    request.parameter = JSON.parse(e.postData.contents)
  } else if (e.parameter) {
    request.parameter = e.parameter
  } else {
    request.status = BAD_REQUEST
    request.message = BAD_REQUEST_MESSAGE
    return request.responseWithJson()
  }

  // Building routes
  var route = new PostRoute()

  route.on(`homeFeatures`, homeFeatureController)

  route.on(`featureDetail`, homeFeatureDetailController)

  route.on(`free2Air`, free2AirController)

  route.on(`checkUpdate`, checkUpdateController)

  route.on(`checkDevicePassword`, checkDevicePasswordController)

  route.on(`checkAdultPassword`, checkAdultPasswordController)

  route.on(`registerDevice`, registerDeviceController)

  route.on(`changeDisplayName`, changeDisplayNameController)

  route.on(`msysFootball`, msysFootballController)

  route.on("tiktok", tiktokController)

  route.on(`myCinema`, myCinemaController)

  route.on(`viu`, viuController)

  // Register the route with request
  request.register(route)

  return request.process()
}
