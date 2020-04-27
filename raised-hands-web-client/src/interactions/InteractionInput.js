import React, {useState} from 'react';

export default ({ createInteraction }) => {
    const [message, setMessage] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        createInteraction(message);
        setMessage('');
    };

    return (
        <div className = "Textbox_at_Bottom" >
            <footer className = "fixed-bottom offset-sm-2">
                <form className="col-md-12" onSubmit={handleSubmit}>
                    <div className="input-group mb-3" >
                        <input
                            type="text"
                            className="form-control"
                            placeholder="Send Message ..."
                            value={message}
                            onChange={(e) => setMessage(e.target.value)}/>
                        <div className="input-group-append" >
                            <button className="btn btn-secondary active" type="submit">Submit</button>
                        </div>
                    </div>
                </form>
            </footer>
        </div>
    );
};
