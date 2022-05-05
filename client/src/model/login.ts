export class LoginCredentials {
    userName: string = ""
    password: string = ""
}

export class LoginTokenResponse {
    credentials: CredentialsResponse = new CredentialsResponse()
}

export class CredentialsResponse {
    userName: string = ""
    accessToken: string = ""
    refreshToken: string = ""
}

export class RefreshBody {
    userName: string = ""
    refreshToken: string = ""
}