const React = require('react');
const ReactDOM = require('react-dom');

import axios from 'axios';

class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {users: []};
	}

	componentDidMount() {
	    axios.get('/api/users')
	    .then(res => {
	        const users = res.data;
	        console.log(users);
	        this.setState({ users: users });
	    })
	}

	render() {
		return (
		    <ul>
		    { this.state.users.map(user => <li key={user.id}>{user.firstName} {user.lastName} {user.email}</li>)}
		    </ul>
		)
	}
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)