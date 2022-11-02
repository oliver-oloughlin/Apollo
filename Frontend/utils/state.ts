import { signal } from "@preact/signals"
import { Account } from "./models.ts"

export const UserSignal = signal<Account | null>(null)