import React from 'react'
import Loader from './Loader';
import CommentForm from './CommentForm';
import styled from 'styled-components';

const UserName = styled.span`
    padding: 10px;
    font-weight: bold;
`

export default class CommentBlock extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            comments: null
        }
    }

    componentDidMount() {
        const id = this.props.bookId;
        fetch(`/api/book/${id}/comments`, {
            method: 'GET',
        }).then((response) => {
            return response.json();
        }).then((value) => {
            this.setState({
                comments: value
            })
        });
    }

    addComment = (newComment) => {
        const newComments = [...this.state.comments, newComment];
        this.setState({ comments: newComments });
    }

    render() {
        const { comments } = this.state;
        const { bookId } = this.props;
        return (
            <React.Fragment>
                <div id="comments">
                    <h3>Комментарии:</h3>
                    {!comments ? <Loader /> : (
                        <React.Fragment>
                            {
                                comments.map(({ sendDate, userName, text }, index) => (
                                    <div className="comment" key={index}>
                                        <div>
                                            <span>{sendDate}</span>
                                            <UserName>{userName}</UserName>
                                        </div>
                                        <div>
                                            <p className="comment-text">{text}</p>
                                        </div>
                                    </div>
                                ))
                            }
                        </React.Fragment>
                    )}
                </div>

                <CommentForm bookId={bookId} addComment={this.addComment} />

            </React.Fragment>
        )
    }

}
