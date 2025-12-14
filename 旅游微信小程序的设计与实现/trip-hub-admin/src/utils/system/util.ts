const toString = Object.prototype.toString

export function isDate (val: any): val is Date {
  return toString.call(val) === '[object Date]'
}

export function isObject (val: any): val is Object {
  return val !== null && typeof val === 'object'
}

export function handleTime (row: any, column: any, cellValue: any, index: any) {
  if(cellValue) {
      return cellValue.replace('T', ' ')
  }
}

export function handleSex (row: any, column: any, cellValue: any, index: any) {
  if(cellValue === 1) {
    return '男'
  } else if (cellValue === 2) {
    return '女'
  }
  return '未知'
}

export function handleAudit (row: any, column: any, cellValue: any, index: any) {
  if(cellValue === 0) {
    return '待审核'
  } else if (cellValue === 1) {
    return '审核通过'
  } else if (cellValue === 2) {
    return '审核不通过'
  }
  return '未知'
}

