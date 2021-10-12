let MSYS_API_KEY = [
  {
    key: 12,
    title: "ယနေ့ပွဲစဉ်များ"
  },
  {
    key: 2,
    title: `Football`,
  },
  {
    key: 8,
    title: `Highlight`,
  },
  {
    key: 11,
    title: `beIn SPORTS`,
  },
]

// Lib
// Cheerio - 1ReeQ6WO8kKNxoaA_O0XEQ589cIrRvEBA9qcWpNqdOP17i47u6N9M5Xh0

const SHEET_ID = `***`

const TIKTOK_BASE_URL = `https://***/node/`

const MYCINEMA_BASE_URL = `https://***/***/api/`

const REQBIN_BASE_URL = `https://***/api/v1/requests`
const TIKTOK_REQUEST_DATA = {
  "id": "0",
  "name": "",
  "errors": "",
  "json": JSON.stringify({
    "method": "GET",
    "url": "https://***/api/***/item_list?aid=1988&app_name=tiktok_mobile&device_platform=mobile&region=MM&timezone_name=Asia%2FRangoon&priority_region=MM&count=30",
    "apiNode": "US",
    "contentType": "",
    "content": "",
    "headers": "user-agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.71 Safari/537.36\nsec-fetch-site: same-site\nsec-gpc: 1\nsec-fetch-mode: cors\nsec-fetch-dest: empty\naccept-language: en-US,en;q=0.9\norigin: https://www.tiktok.com/\nreferer: https://www.tiktok.com/\ncookie:tt_webid_v2=7015032070158845445; tt_webid=7015032070158845445; tt_csrf_token=2FL1vrl8tQbJ5A9SOMx8HmKB; R6kq3TV7=AKu3IEl8AQAA8fV7AxAyjTGrOCLnJo3pXp2AOFyr7bY3BnCsDoUmPcSyL1LI|1|0|b3b19f9b1acaf402da78bcb6d3bd3de09f46b6cd; cookie-consent={%22ga%22:true%2C%22af%22:true%2C%22fbp%22:true%2C%22lip%22:true%2C%22version%22:%22v2%22}; passport_csrf_token_default=ee59762ff6c85b8809de84ebc1861ef9; passport_csrf_token=ee59762ff6c85b8809de84ebc1861ef9; d_ticket=426c110263902af1713dc904c3f49d71c8589; passport_auth_status=b76730a9462e6d72fcb17ece10638e37%2C275377176d20bf1205da075e03943d8b; passport_auth_status_ss=b76730a9462e6d72fcb17ece10638e37%2C275377176d20bf1205da075e03943d8b; sid_guard=e19cb0f6deba4cc981e9d8b1d2325375%7C1633318324%7C5184000%7CFri%2C+03-Dec-2021+03%3A32%3A04+GMT; uid_tt=86b44a20fd1761c47ac9b298087aaa2a06b07f7229116fc7142a6fa3072781d4; uid_tt_ss=86b44a20fd1761c47ac9b298087aaa2a06b07f7229116fc7142a6fa3072781d4; sid_tt=e19cb0f6deba4cc981e9d8b1d2325375; sessionid=e19cb0f6deba4cc981e9d8b1d2325375; sessionid_ss=e19cb0f6deba4cc981e9d8b1d2325375; sid_ucp_v1=1.0.0-KDM0NTY0M2E5YTkyMWJmZmE3MmM2ZTVkNmUzYjcxZDI5NTBiYjhhNjEKHwiBgJ2qmrKd01oQtOvpigYYswsgDDD6xvHbBTgIQAoQAxoGbWFsaXZhIiBlMTljYjBmNmRlYmE0Y2M5ODFlOWQ4YjFkMjMyNTM3NQ; ssid_ucp_v1=1.0.0-KDM0NTY0M2E5YTkyMWJmZmE3MmM2ZTVkNmUzYjcxZDI5NTBiYjhhNjEKHwiBgJ2qmrKd01oQtOvpigYYswsgDDD6xvHbBTgIQAoQAxoGbWFsaXZhIiBlMTljYjBmNmRlYmE0Y2M5ODFlOWQ4YjFkMjMyNTM3NQ; store-idc=alisg; store-country-code=mm; msToken=veZvf1xerEu8qj_3IyWM6mVKtcyEwTeJKaIYT5Smujlfgr2qnISWVcr89mt9rAvf6jE7gjVyK6WMfVgW2oSE7-bdqpHkH69T6HrYtJ1PBSywCteREKlMK_5Tvcn4sQ==; ttwid=1%7Ctd9pv71_RJypdfJAy4sonQRINQiMV6Z8kX7iKNa9usk%7C1633319421%7C256bb03c21c13a5a82063afa39c17c41fbf3f88f4baf3e77784b50516198f0ea; cmpl_token=AgQQAPPhF-RMpYqYmzYtdZk4-zv_s4pVv4dqYPz41Q; odin_tt=98063dec65145bc68e7efc46316a0a20f0ad37f818dda7b58d10e7c8c1e6fbd15ad12a9a01ce151fa0e7fd892624c9d12a3f1891744d463e2967c6993358bf44",
    "errors": "",
    "curlCmd": "",
    "codeCmd": "",
    "auth": {
      "auth": "noAuth",
      "bearerToken": "",
      "basicUsername": "",
      "basicPassword": "",
      "customHeader": "",
      "encrypted": ""
    },
    "compare": false,
    "idnUrl": "https://***/api/***/item_list?aid=1988&priority_region=MM&count=30"
  }),
  "deviceId": "",
  "sessionId": ""
}

const VIU_LANGUAGE = {
  en: {
    title: `English`,
    key: `en`
  },
  id: {
    title: `Indonesia`,
    key: `id`
  },
  mya: {
    title: `Myanmar`,
    key: `mya`
  },
  my: {
    title: `Myanmar - Zawgyi`,
    key: `my`
  },
  ms: {
    title: `Malaysia`,
    key: `ms`
  },
  "zh-CN": {
    title: `China`,
    key: `zh-cn`
  },
  ar: {
    title: `عربى`,
    key: `ar`
  }
}

const VIU_DRAWER_MENU_LIST = [
  {
    drawer_key: `burmese`,
    drawer_title: `Burmese`,
    drawer_type: `list`
  },
  {
    drawer_key: `indian`,
    drawer_title: `Indian`,
    drawer_type: `list`
  },
  {
    drawer_key: `korean`,
    drawer_title: `Korean`,
    drawer_type: `list`
  },
  {
    drawer_key: `thai`,
    drawer_title: `Thai`,
    drawer_type: `list`
  },
  {
    drawer_key: `227_0_30_2.mm`,
    drawer_title: `Just Added`,
    drawer_type: `grid`
  },
  {
    drawer_key: `playlist-25974477`,
    drawer_title: `Exclusive Movies`,
    drawer_type: `grid`
  },
  {
    drawer_key: `playlist-26256994`,
    drawer_title: `Only On Viu`,
    drawer_type: `grid`
  },
  {
    drawer_key: `228_0_30_2.mm`,
    drawer_title: `Battle Of Two flowers`,
    drawer_type: `grid`
  },
  {
    drawer_key: `232_0_30_2.mm`,
    drawer_title: `Kya Mor Non`,
    drawer_type: `grid`
  },
  {
    drawer_key: `226_0_30_2.mm`,
    drawer_title: `Binge-watch Yadanarbon`,
    drawer_type: `grid`
  },
  {
    drawer_key: `242_0_30_2.mm`,
    drawer_title: `Latest Korean Series`,
    drawer_type: `grid`
  },
  {
    drawer_key: `247_0_30_2.mm`,
    drawer_title: `Jeet Gayi Toh Piya More`,
    drawer_type: `grid`
  },
  {
    drawer_key: `241_0_30_2.mm`,
    drawer_title: `Hot on Viu`,
    drawer_type: `grid`
  },
  {
    drawer_key: `239_0_30_2.mm`,
    drawer_title: `Just Added Indian and Thai`,
    drawer_type: `grid`
  },
  {
    drawer_key: `244_0_30_2.mm`,
    drawer_title: `Jodha Akbar S4`,
    drawer_type: `grid`
  },
  {
    drawer_key: `playlist-25719041`,
    drawer_title: `Handpicked Comedy`,
    drawer_type: `grid`
  },
  {
    drawer_key: `playlist-26270439`,
    drawer_title: `Superhit Indian Movies`,
    drawer_type: `grid`
  },
  {
    drawer_key: `248_0_30_2.mm`,
    drawer_title: `Ashoka - Season 1`,
    drawer_type: `grid`
  },
  {
    drawer_key: `240_0_30_2.mm`,
    drawer_title: `Most Popular K- Drama`,
    drawer_type: `grid`
  },
  {
    drawer_key: `201_0_30_2.mm`,
    drawer_title: `Mingalar Shi Tae A Yat`,
    drawer_type: `grid`
  },
]

const VIU_DEVICE_ID = `****-****-****-****`

const VIU_REQUEST_HEADER = {
  "x-client": `****`,
  "x-session-id": 1
}

const VIU_BASE_URL = `https://****/****/****/api/`
const VIU_IMAGE_BASE_URL = `https://****/p/****/****/`
const VIU_DRAWER_BASE_URL = `https://****/program/prod/****/****/mm/default/****/`

const DEVICE_SHEET = `Device`
const HOME_FEATURE_SHEET = `Home Feature`
const FREE_2_AIR_SHEET = `Free 2 Air`
const CHECK_UPDATE_SHEET = `Check Update`
const SPECIAL_FEATURE_SHEET = `Special Feature`
const ACCESS_DEVICE_SHEET = `Access Device ID`

const POST = "post"
const GET = "get"

const APPLICATION_JSON = `application/json`

const SUCCESS = 200
const NOT_FOUND = 404
const INTERNAL_ERROR = 500
const BAD_REQUEST = 400
const UNAUTHORIZED_REQUEST = 403

const SUCCESS_MESSAGE = `Success`
const BAD_REQUEST_MESSAGE = `Bad request.`
const NOT_FOUND_MESSAGE = `Not found.`
const ROUTE_NOT_FOUND = `Route not found.`
const INTERNAL_ERROR_MESSAGE = `Internal server error.`
const UNAUTHORIZED_REQUEST_MESSAGE = `Unauthorized request.`
