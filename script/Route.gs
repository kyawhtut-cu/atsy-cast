function Route() {
  this.routes = []
}

Route.prototype.on = function (route, callback) {
  this.routes.push({ 'route': route, 'callback': callback })
}

Route.prototype.condition = function (request) {
  return request.parameter.method != null
}

Route.prototype.handle = function (request) {
  for (var index in this.routes) {
    var route = this.routes[index]
    if (request.parameter.method == route.route) {
      return route.callback(request)
    }
  }

  request.status = NOT_FOUND
  request.message = ROUTE_NOT_FOUND
  return request.responseWithJson()
}
