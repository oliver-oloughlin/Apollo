export function regExpToString(regExp: RegExp) {
  const str = regExp.toString()
  return regExp.toString().substring(1, str.length - 1)
}

export const PasswordValidator = /^.{8,64}$/