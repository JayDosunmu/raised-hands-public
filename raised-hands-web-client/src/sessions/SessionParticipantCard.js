import React from 'react';
import Image from './Assets/trophy.svg'

export default ({ participant }) => (
    <div className="card border-dark mb-3" style={{ maxWidth: '12em', maxHeight: '5em',}}>
        <div className="card-body">
            { participant.leader && <img src={Image}/> }
            <span>{participant.account.name}</span>
        </div>
    </div>
);
