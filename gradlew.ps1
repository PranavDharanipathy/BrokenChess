$script = Join-Path $PSScriptRoot "gradlew.bat"
if (-not (Test-Path $script)) {
    throw "gradlew.bat was not found in $PSScriptRoot"
}

& $script @args
exit $LASTEXITCODE
