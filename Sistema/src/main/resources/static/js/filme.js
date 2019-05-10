var commentData = [{

        author: "Cot",
        text: "Miau"

    },

    {
        author: "Dag",
        text: "Auau"
    }
];


var CommentBox = React.createClass({
    displayName: "CommentBox",
    getInitialState: function() {
        return {
            data: commentData
        };

    },
    handleCommentSubmit: function(comment) {
        this.props.data.push(comment);
        var comments = this.state.data;
        var newComments = comments.concat([comment]);
        this.setState({ data: newComments });
    },
    render: function() {
        return (
            React.createElement("div", { className: "comment-box" },
                React.createElement(CommentForm, { data: this.props.data, onCommentSubmit: this.handleCommentSubmit }),
                React.createElement(CommentList, { data: this.props.data })));


    }
});

var CommentList = React.createClass({
    displayName: "CommentList",
    render: function() {
        return (
            React.createElement("div", { className: "comment-list" },
                this.props.data.map(function(c) {
                    return (
                        React.createElement(Comment, { author: c.author, text: c.text }));

                })));


    }
});

var CommentForm = React.createClass({
    displayName: "CommentForm",
    handleSubmit: function(e) {
        e.preventDefault();
        var authorVal = e.target[0].value.trim();
        var textVal = e.target[1].value.trim();
        if (!textVal || !authorVal) {
            return;
        }
        this.props.onCommentSubmit({ author: authorVal, text: textVal });
        e.target[0].value = '';
        e.target[1].value = '';
        return;
    },
    render: function() {
        return (
            React.createElement("form", { className: "comment-form form-group", onSubmit: this.handleSubmit },
                React.createElement("div", { className: "input-group" },
                    React.createElement("span", { className: "input-group-addon" }, "Nome"),
                    React.createElement("input", { type: "text", placeholder: "Your name", className: "form-control" })),

                React.createElement("div", { className: "input-group" },
                    React.createElement("span", { className: "input-group-addon" }, "Resenha"),
                    React.createElement("input", { type: "text", placeholder: "Say something...", className: "form-control" })),

                React.createElement("input", { type: "button", value: "Postar", className: "btn btn-primary" })));


    }
});

var Comment = React.createClass({
    displayName: "Comment",
    render: function() {
        return (
            React.createElement("div", { className: "comment" },
                React.createElement("h2", { className: "author" }, this.props.author),
                this.props.text));


    }
});

React.render(
    React.createElement(CommentBox, { data: commentData }),
    document.getElementById('app'));

$(document).ready(
    function() {
        $('#button').click(
            function() {
                var toAdd = $('input[name=ListItem]').val();
                $('ol').append('<li>' + toAdd + '</li>');
            });

        $("input[name=ListItem]").keyup(function(event) {
            if (event.keyCode == 13) {
                $("#button").click();
            }
        });

        $(document).on('dblclick', 'li', function() {
            $(this).toggleClass('strike').fadeOut('slow');
        });

        $('input').focus(function() {
            $(this).val('');
        });

        $('ol').sortable();

    }
);