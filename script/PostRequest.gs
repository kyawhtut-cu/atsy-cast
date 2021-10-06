function PostRequest(parameter) {
  this.parameter = parameter
  this.status = SUCCESS
  this.message = SUCCESS_MESSAGE
  this.response = null
  this.handlers = []
}

PostRequest.prototype.process = function () {
  for (var index in this.handlers) {
    var route = this.handlers[index]
    var result = route.condition(this)
    if (result) {
      return route.handle(this)
    } else {
      this.status = BAD_REQUEST
      this.message = BAD_REQUEST_MESSAGE
      return this.responseWithJson()
    }
  }
}

PostRequest.prototype.register = function (handler) {
  this.handlers.push(handler)
}

PostRequest.prototype.responseWithJson = function () {
  var responseData = {
    status: this.status,
    message: this.message,
    data: this.response ? this.response : null
  }
  return ContentService.createTextOutput(JSON.stringify(responseData)).setMimeType(ContentService.MimeType.JSON)
}
