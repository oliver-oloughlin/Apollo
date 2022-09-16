export function redirect(redirectURL: string) {
  const headers = new Headers({
    location: redirectURL
  })

  return new Response(null, {
    status: 302,
    headers
  })
}