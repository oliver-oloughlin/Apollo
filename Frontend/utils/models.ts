export type Customer = {
  username: string,
  password: string,
  admin: boolean
}

export type Poll = {
  id: number,
  name: string,
  private: boolean
}

export type Vote = {
  id: number,
  poll: Poll,
  green: number,
  red: number
}