export interface Customer {
  username: string,
  password: string,
  admin: boolean
}

export interface Poll {
  code: number,
  title: string,
  timeToStop: string,
  privatePoll: boolean,
  closed: boolean,
  ownerEmail: string,
  questionIds: number[]
}

export interface Question {
  id: number,
  text: string,
  voteIds: number[],
  deviceTokens: number[]
}

export interface Vote {
  id: number,
  poll: Poll,
  green: number,
  red: number
}

type AccountType = "Normal" | "Facebook" | "Google"

export interface SignUpData {
  email?: string,
  password?: string,
  accountType: AccountType
}

export interface SignInData {
  email?: string,
  password?: string,
  accountType: AccountType
}