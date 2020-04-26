import React from 'react';

export default class InteractionEvents extends React.Component {
    constructor(props) {
        super(props);
        console.log(props);

    }

render() {
    return (
<div>



<div className = "Textbox_at_Bottom" >
    <footer className = "fixed-bottom offset-sm-2">
        <div className="col-md-12" >
            <div className="input-group mb-3" >
                <div className="input-group-prepend" >
                    <button className="btn btn-secondary active" type="button">Submit</button>
                        </div>
                             <input type="text" className="form-control" placeholder="Send Message ..." aria-label="" aria-describedby="basic-addon1" >
                              </input>
                </div>
        </div>
    </footer> 
</div>  
</div>
    );
}

}
