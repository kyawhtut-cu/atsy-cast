function doGet(e) {

  let sheet_no = e.parameter.sheet_no

  if(sheet_no == 2) {
    return homeFeatureControllerOld()
  } else if(sheet_no == `msysFootball,4`) {
    return msysFootballControllerOld()
  }

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

  route.on(`myCinema`, myCinemaController)

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
