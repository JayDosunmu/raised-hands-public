import React from "react";



export default class SessionListViewCard extends React.Component {
  constructor(props) {
      super(props);
      this.state = {
        sessionId: null,
        sessions: {},
      };
  }

  render() {
    
    return (

        <div className = "SessionListCards">
            <div className="card text-white mb-3 bg-success SessionListActiveCard" >
                <div class="card-header">name of session</div>
                <div className="card-body">
            
                </div>
            </div>

            <div className="card text-white mb-3 bg-secondary SessionListInactiveCard" >
                <div className="card-body">
                <div class="card-header">name of session</div>
                </div>
            </div>
        
        </div>
    );
}
}