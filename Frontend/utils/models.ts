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
  pollCode: number
}

export interface Vote {
  questionId: number,
  green: number,
  red: number,
  voterEmail: string
}

export interface Account {
  email: string,
  password: string,
  isAdmin: boolean,
  pollCodes: number[],
  voteIds: number[]
}

export interface AccountCredentials {
  email?: string,
  password?: string
}