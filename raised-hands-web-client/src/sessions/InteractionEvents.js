import React from 'react';

export default class InteractionEvents extends React.Component {
    constructor(props) {
        super(props);
        this.state ={
        interactionId: '',
        sessionId: '',
        sessionParticipantId: '',
        message: '',
        cleared: false,
        timestamp: '',
        vote: ''
    }
    }
    
    handleChange(event) {
        this.setState({[event.target.name]: event.target.value});
      }
    
      handleSubmit(event) {
        alert('A interaction event was submitted: ' + this.state.value);
        event.preventDefault();
      }
   
render() {
    return (
<div className = "col d-inline-block h-100">
    <div className = " InteractionsBox col d-inline-block h-100">
    </div>

    <footer className = "Textbox_at_Bottom fixed-bottom offset-sm-2">
        <div className="col-md-12" >
            <div className="input-group mb-3" >
                <div className="input-group-prepend" >
                    <button className="btn btn-secondary active" type="button">Submit</button>
                        </div>
                             <input type="text" className="form-control" placeholder="Send Message ..." aria-label="" aria-describedby="basic-addon1" value={this.state.value} onChange={this.handleChange} >
                              </input>
                </div>
        </div>
    </footer> 
</div>
    );
}
}
