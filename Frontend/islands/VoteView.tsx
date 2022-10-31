import { useState } from "preact/hooks"
import { Fragment } from "preact"
import { Poll, Question } from "../utils/models.ts"

interface Data {
  poll: Poll,
  questions: Question[]
}

export default function VoteView({ poll, questions }: Data) {
  const [questionIndex, setQuestionIndex] = useState<number>(0)
  const question = questions[questionIndex]

  function castVote({ green, red }: { green: number, red: number}) {
    // TODO: Process and send vote to API
    console.log("Casting")
    setQuestionIndex(index => index + 1)
  }

  return (
    <div class="poll-container">
      {question &&
        <Fragment>
        <div class="question-container centered-text">
          <p>{question.text}</p>
        </div>
        <div class="switch">
          <button onClick={() => castVote({ green: 0, red: 1 })} class="btn-red switch-btn" />
          <button onClick={() => castVote({ green: 1, red: 0 })} class="btn-blue switch-btn" />
        </div>
        </Fragment> 
      }
      {!question &&
        <p>There are no more questions, thanks for voting!</p>
      }
    </div>
  )
}