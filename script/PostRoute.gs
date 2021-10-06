function PostRoute() {
  this.routes = []
}

PostRoute.prototype.on = function (route, callback) {
  this.routes.push({ 'route': route, 'callback': callback })
}

PostRoute.prototype.condition = function (request) {
  return request.parameter.route != null
}

PostRoute.prototype.handle = function (request) {
  for (let index in this.routes) {
    const route = this.routes[index]
    if (request.parameter.route == route.route) {
      return route.callback(request)
    }
  }

  request.status = NOT_FOUND
  request.message = ROUTE_NOT_FOUND
  return request.responseWithJson()
}
