import React from 'react';

export default class SessionParticipateCard extends React.Component {
    constructor(props) {
        super(props);
        console.log(props);
    }
    render() {
        let name = this.props.participant.account.name;
        let leaderStatus = "Leader = " + (this.props.participant.leader).toString();

        return (
            <div className="card text-white bg-success mb-3" style={{ maxWidth: '18em', maxHeight: '20em' }}>
                <h4 className="card-header">{name}</h4>
                <div className="card-body">
                    {this.props.participant.leader && <h5 className="card-title">{leaderStatus} </h5>}
                    <p className="card-text"></p>
                </div>
            </div>


        );
    }

}
