/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {},
  },
  plugins: [require("daisyui")],
  daisyui: {
    // themes: ["light","light"],
    themes: ["dim","light",{
        light: {
          ...require("daisyui/src/theming/themes")["light"],
          primary: "0039cc",
          ".sprint-bg":{
            "background-color":"#f7f8f9"
          }
        }
      },
      {
        dim: {
          ...require("daisyui/src/theming/themes")["dim"],
          ".sprint-bg":{
            "background-color":"#1f242d"
          }
        },
    },],
  },
}

