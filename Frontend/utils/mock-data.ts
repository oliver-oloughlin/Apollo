import { Poll, Question } from "./models.ts"

export const MockQuestion1: Question = {
  text: "Is it cold today?",
  id: 1,
  voteIds: []
}

export const MockQuestion2: Question = {
  text: "Is it hot today?",
  id: 2,
  voteIds: []
}

export const MockQuestion3: Question = {
  text: "Who are you talking to right now? Who is it you think you see?",
  id: 3,
  voteIds: []
}

export const MockPoll1: Poll = {
  title: "Mocking Poll",
  code: 1,
  privatePoll: true,
  closed: false,
  timeToStop: "13:37",
  ownerEmail: "oli@gmail.com",
  questionIds: [1]
}

export const MockPoll2: Poll = {
  title: "Smokey the poll",
  code: 2,
  privatePoll: false,
  closed: false,
  timeToStop: "22:22",
  ownerEmail: "andy@gmail.com",
  questionIds: [2]
}

export const MockPoll3: Poll = {
  title: "Polly Duck",
  code: 3,
  privatePoll: false,
  closed: false,
  timeToStop: "23:59",
  ownerEmail: "inge@gmail.com",
  questionIds: [3]
}

export const MockPolls: Poll[] = [MockPoll1, MockPoll2, MockPoll3]