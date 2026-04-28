_default:
    @just --list

mvnw_cmd := if os() == "windows" { "mvnw.cmd" } else { "./mvnw" }

# view the control panel
[working-directory('backend')]
backend:
    {{ mvnw_cmd }} javafx:run

# start the frontend development server
[working-directory('frontend')]
frontend-dev:
    pnpm run dev