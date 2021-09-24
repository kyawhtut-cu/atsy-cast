function getSheetByIndex(sheetID, sheetIndex) {
  return SpreadsheetApp.openById(sheetID).getSheets()[sheetIndex]
}

function getSheetByName(sheetID, sheetName) {
  return SpreadsheetApp.openById(sheetID).getSheetByName(sheetName)
}

function getSheetNameBySheetIndex(sheetID, sheetIndex) {
  return getSheetByIndex(sheetID, sheetIndex).getName()
}

function getSheetNameBySheetName(sheetID, sheetName) {
  return getSheetByName(sheetID, sheetName).getName()
}

function findRowIndex(sheetID, sheetIndex, headerKey, dataValue) {
  let sheet = null
  if (Number.isInteger(parseInt(sheet))) {
    sheet = getSheetByIndex(sheetID, sheetIndex)
  } else {
    sheet = getSheetByName(sheetID, sheetIndex)
  }

  let foundIndex = -1
  if (sheet != null) {
    let sheetData = sheet.getDataRange().getValues()
    if (sheetData.length != 0) {
      let headerIndex = sheetData[0].indexOf(headerKey)
      if (headerIndex != -1) {
        for (var index = 1; index < sheetData.length; index++) {
          if (sheetData[index][headerIndex] == dataValue) {
            foundIndex = index + 1
            break
          }
        }
      }
    }
  }
  return foundIndex
}

function findColumnIndex(sheetID, sheetIndex, headerKey) {
  let sheet = null;
  if (Number.isInteger(parseInt(sheet))) {
    sheet = getSheetByIndex(sheetID, sheetIndex)
  } else {
    sheet = getSheetByName(sheetID, sheetIndex)
  }

  let foundIndex = -1
  if (sheet != null) {
    let sheetData = sheet.getDataRange().getValues()
    if (sheetData.length != 0) {
      foundIndex = sheetData[0].indexOf(headerKey)
      if (foundIndex != -1) {
        foundIndex += 1
      }
    }
  }
  return foundIndex
}

function getSheetDataAsJson(sheetID, sheetIndex) {
  let sheet = null;
  if (Number.isInteger(parseInt(sheet))) {
    sheet = getSheetByIndex(sheetID, sheetIndex)
  } else {
    sheet = getSheetByName(sheetID, sheetIndex)
  }
  let sheetData = sheet.getDataRange().getValues()

  if (sheetData.length == 0) return [];

  let sheetRowHeader = sheetData[0];
  let responseData = [];

  sheetData.forEach((value, key) => {
    if (key != 0) {
      let tmp = {};
      value.forEach((data, index) => {
        tmp[sheetRowHeader[index]] = data;
      })
      responseData.push(tmp);
    }
  });

  return responseData;
}
