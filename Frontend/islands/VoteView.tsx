import { useEffect, useState } from "preact/hooks"
import { Fragment } from "preact"
import { Poll, Question } from "../utils/models.ts"
import { API_HOST } from "../utils/api.ts"

interface ResData {
  redirect?: string,
  poll?: Poll,
  questions?: Question[]
}

async function fetcher(): Promise<ResData> {
  try {
    const code = new URLSearchParams(location.search).get("code")
    const res = await fetch(`${API_HOST}/poll/${code}`)

    if (!res.ok) {
      let redirect = "/"
      if (res.status === 401) redirect = `/sign-in?next=${location.origin}`
      return { redirect }
    }

    const poll = await res.json() as Poll
    const qFetchers = poll.questionIds.map(id => fetch(`${API_HOST}/question/${id}`).then(res => res.json())) as Promise<Question>[]
    const questions = await Promise.all(qFetchers)
    return {
      poll,
      questions
    }
  }
  catch (err) {
    console.error(err);
    return { redirect: "/" }
  }
}

export default function VoteView() {
  const [poll, setPoll] = useState<Poll | null>(null)
  const [questions, setQuestions] = useState<Question[] | null>(null)
  const [questionIndex, setQuestionIndex] = useState<number>(0)
  const question = questions?.at(questionIndex)

  useEffect(() => {
    fetcher()
      .then(({ redirect, poll, questions}) => {
        if (redirect) {
          window.open(redirect, "_self")
          return
        }
        setPoll(poll!)
        setQuestions(questions!)
      })
  }, [])

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
        <h1>There are no more questions, thanks for voting!</h1>
      }
    </div>
  )
}