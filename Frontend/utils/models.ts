export type Customer = {
  username: string,
  password: string,
  admin: boolean
}

export type Poll = {
  id: number,
  customer: Customer,
  greenVotes: number,
  redVotes: number,
  private: boolean
}

export type Vote = {
  id: number,
  poll: Poll,
  green: number,
  red: number
}