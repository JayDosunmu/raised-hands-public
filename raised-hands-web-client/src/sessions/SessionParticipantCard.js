import React from 'react';
import Image from './Assets/trophy.svg'

export default class SessionParticipateCard extends React.Component {
    constructor(props) {
        super(props);
        console.log(props);
        this.state = {
            websocket: null,
            participants: {}
        };
    }
    
    render() {
        let name = this.props.participant.account.name;
        return (
  
                <div className="card border-dark mb-3" style={{ maxWidth: '12em', maxHeight: '5em',}}>
                    <div className="card-body">
                        <h5 >{name}</h5>
                        {this.props.participant.leader && <img src={Image}></img>} 
                    </div>
                </div>
       
        );
    }

}
