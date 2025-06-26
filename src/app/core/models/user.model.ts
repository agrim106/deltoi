export interface UserDto {
  id: number;
  username: string;
  email: string;
  createdAt: string;
}

export interface UserSignupRequest {
  username: string;
  email: string;
  password: string;
}

export interface UserLoginRequest {
  email: string;
  password: string;
}
