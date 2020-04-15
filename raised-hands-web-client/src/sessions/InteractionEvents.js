import React from 'react';

export default class InteractionEvents extends React.Component {
    constructor(props) {
        super(props);
        console.log(props);

    }

    render() {
        return (
            <div>
                
                <ul>
                    <h2>INTERACTION COLUMN</h2>
                    <div className="form-group" style={{maxWidth: '40em'}}>
                        <label htmlFor="exampleFormControlTextarea1">Ask Question</label>
                        <textarea className="form-control"  id="exampleFormControlTextarea1" rows="5"></textarea>
                    </div>
                </ul>

            </div>
        );
    }

}
