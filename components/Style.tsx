/** @jsx h */
import { h } from "preact";
import { asset } from "$fresh/runtime.ts";

export default function Style({ fileName }: { fileName: string }) {
  return <link rel="stylesheet" href={asset(`/styling/${fileName}`)} />;
}
