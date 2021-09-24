function doGet(e) {

  // Instantiate Request
  var request = new Request(e.parameter)

  // Building routes
  var route = new Route()

  route.on(`homeFeatures`, homeFeatureController)

  route.on(`free2Air`, free2AirController)

  route.on(`checkDevicePassword`, checkDevicePasswordController)

  route.on(`checkAdultPassword`, checkAdultPasswordController)

  route.on(`registerDevice`, registerDeviceController)

  route.on(`changeDisplayName`, changeDisplayNameController)

  route.on(`checkUpdate`, checkUpdateController)

  route.on(`msysFootball`, msysFootballController)

  // Register the route with request
  request.register(route)

  return request.process()
}

function doPost(e) {

  // Instantiate Request
  var request = new Request(null)

  if (e.postData && e.postData.contents) {
    request.parameter = JSON.parse(e.postData.contents)

    // Building routes
    var route = new Route()

    // route.on("routeName", routeControllers)

    // Register the route with request
    request.register(route)

    return request.process()
  }

  request.status = BAD_REQUEST
  request.message = BAD_REQUEST_MESSAGE
  return request.response()
}
