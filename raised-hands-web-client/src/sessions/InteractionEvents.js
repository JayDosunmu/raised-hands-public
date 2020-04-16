import React from 'react';

export default class InteractionEvents extends React.Component {
    constructor(props) {
        super(props);
        console.log(props);

    }

render() {
    return (
        <div>
        <div className = "Ask-Question">
            <footer className="fixed-bottom">
                <div className="form-group">
                    <div className="text-box">
                        <label htmlFor="TextArea" className = "textColor">Ask Question</label>
                        <textarea className="form-control" id="TextArea" rows="5"></textarea>
                        <button type="submit" class="btn btn-primary mb-2">Send</button>
                    </div>
                </div>
            </footer>

            <div>
               
            </div>
        </div>
        
        <div className = "InteractionEventsHeaderTitle">
         <h2>Class Room</h2>
         </div>

         </div>
    );
}

}
