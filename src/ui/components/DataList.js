import React from 'react';

class DataList extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        const { list, title } = this.props;
        return (
            !list ? null :
                <div>
                    <b>{title}</b>
                    <ul>
                        {
                            list.map((item, index) => (
                                <li key={index}>{item}</li>
                            ))
                        }
                    </ul>
                </div>
        )
    }
}

export default DataList;
