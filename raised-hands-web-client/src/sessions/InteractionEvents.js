import React from 'react';

export default class InteractionEvents extends React.Component {
    constructor(props) {
        super(props);
        console.log(props);

    }

render() {
    return (

<div className = "Textbox_at_Bottom">
 <footer className = "fixed-bottom offset-sm-2">
<div class="col-md-12">
<div class="input-group mb-3">
  <div class="input-group-prepend">
    <button class="btn btn-secondary active" type="button">Submit</button>
  </div>
  <input type="text" class="form-control" placeholder="" aria-label="" aria-describedby="basic-addon1">
      </input>
</div>
</div>
</footer> 
</div>  
    );
}

}
