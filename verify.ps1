$headers = @{'X-USER-ID' = 'verify_user' }
Write-Output "Testing Rate Limit for User: verify_user"
for ($i = 1; $i -le 12; $i++) {
    try {
        $response = Invoke-WebRequest -Uri "http://localhost:8080/api/resource" -Headers $headers -Method Get -ErrorAction Stop -UseBasicParsing
        Write-Output "Req $i : $($response.StatusCode) (Allowed)"
    }
    catch {
        # Check if it is a 429
        if ($_.Exception.Response.StatusCode -eq 429) {
            Write-Output "Req $i : 429 (Blocked) - SUCCESS"
        }
        else {
            Write-Output "Req $i : $($_.Exception.Response.StatusCode) (Unexpected)"
        }
    }
    Start-Sleep -Milliseconds 200
}

Write-Output "`n--- Usage Stats ---"
try {
    $stats = Invoke-RestMethod -Uri "http://localhost:8080/api/usage" -ErrorAction Stop
    $stats | ConvertTo-Json -Depth 2
}
catch {
    Write-Output "Failed to fetch stats: $_"
}
