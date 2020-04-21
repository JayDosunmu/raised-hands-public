import React from 'react';

export default class SessionParticipateCard extends React.Component {
    constructor(props) {
        super(props);
        console.log(props);
    }

    render() {

        let name = this.props.participant.account.name;
        let leaderStatus ;
        
        if((this.props.participant.leader)){
             leaderStatus = "Leader ";
        }
    
        return (
            
                <div className="card border-dark mb-3" style={{ maxWidth: '12em', maxHeight: '5em',}}>
                    <div className="card-body">
                        <h5 >{name}</h5>
                        {this.props.participant.leader && <h5 className="card-title">{leaderStatus} </h5>}
                        
                    </div>
                </div>
       
        );
    }

}
