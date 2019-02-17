import React from 'react';

export default class DataList extends React.Component {

    static defaultProps = {
        list: []
    }

    render() {
        const { list, title } = this.props;
        return (
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
