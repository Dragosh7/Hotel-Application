import React from "react";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import api from './api/axiosConfig';
import history from "./history";

class Login extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: "",
            password: "",
            loginSuccess: {
                username: "",
                role: "",
            },
            message: "",
        };
    }
    componentDidMount() {
        const user = localStorage.getItem("user");
        const role = localStorage.getItem("role");
        if (user && role) {
            this.setState({
                message: "Please logout first before logging in again.",
            });
            history.push("/");
        }
    }
    handleInput = (event) => {
        const { value, name } = event.target;
        this.setState({
            [name]: value
        });
    };

    onSubmitFunction = (event) => {
        event.preventDefault();
        const { username, password } = this.state;

        const credentials = {
            username,
            password
        };

        api.post("/users/login", credentials)
            .then((res) => {
                const val = res.data;
                this.setState({
                    loginSuccess: val
                });
                localStorage.setItem("user", val.username);
                localStorage.setItem("role", val.role);
                history.push("/");
                window.location.reload();
            })
            .catch((error) => {
                console.error("Login error:", error);
                this.setState({
                    message: "Invalid username or password.",
                });
            });
    };
    logout = () => {
        localStorage.removeItem("user");
        localStorage.removeItem("role");
        history.push("/login");
        };

    render() {
        return (
            <Container maxWidth="sm">
                <Grid container spacing={2}>
                    <Grid item xs={12}>
                        {this.state.message && (
                            <p style={{ color: "red" }}>{this.state.message}</p>
                        )}
                        <form onSubmit={this.onSubmitFunction}>
                            <TextField
                                variant="outlined"
                                margin="normal"
                                required
                                fullWidth
                                id="username"
                                label="Username"
                                name="username"
                                autoComplete="username"
                                onChange={this.handleInput}
                                autoFocus
                            />
                            <TextField
                                variant="outlined"
                                margin="normal"
                                required
                                fullWidth
                                name="password"
                                label="Password"
                                type="password"
                                id="password"
                                autoComplete="current-password"
                                onChange={this.handleInput}
                            />
                            <Button
                                type="submit"
                                fullWidth
                                variant="contained"
                                color="primary"
                            >
                                Sign In
                            </Button>
                        </form>
                    </Grid>
                </Grid>
            </Container>
        );
    }
}

export default Login;
