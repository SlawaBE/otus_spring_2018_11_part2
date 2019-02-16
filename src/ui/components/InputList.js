import React from 'react';

class InputList extends React.Component {

    render() {
        var { list } = this.props;
        return (
            <div className="row">
                <div>
                    <label>{this.props.title}</label>
                    {
                        list.map((item, index) => (
                            <div key={index}>
                                <input type="text" value={item} onChange={(event) => {
                                    list[index] = event.target.value;
                                    this.props.updateParentState(list);
                                }} />
                                <input type="submit" value='-' onClick={(() => {
                                    list.splice(index, 1);
                                    this.props.updateParentState(list);
                                })} />
                            </div>
                        ))
                    }
                </div>
                <div>
                    <input type="submit" value='+' onClick={() => {
                        list.push('');
                        this.props.updateParentState(list);
                    }} />
                </div>
            </div>
        )
    }

}

export default InputList;
