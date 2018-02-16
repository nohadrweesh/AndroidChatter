$dbHost="";
$dbUsername="";
$dbPassword="";
$dbName="";

$db=new MySQL($dbHost,$dbUsernaem,$dbPassword,$dbName);
$username =(isset($_REQUEST['username'])&&($_REQUEST['username'])>0)?$_REQUEST['username']:NULL;
$password =(isset($_REQUEST['password'])?md5($_REQUEST['password']):NULL;
$port =(isset($_REQUEST['port'])?$_REQUEST['port']:NULL;
$action=(isset($_REQUEST['action'])?$_REQUEST['action']:NULL;

if($action=="testWebAPI){

    if($db->testConnection()){
        echo "Sucessfull";
        exit;
    }else{
        echo "failed";
        exit;
    }
}

switch($action){
    case authenticateIser:
        if($userId=authenticateIser($db,$username,$password){
            $sql = "SELECT .Id, u.username,u.IP,
            f.providerId, f.requestId, f.status, u.port from friends f left join users u on 
            u.Id = if (f.providerId = ".$userId.", f.requestId, f.providerId)
            where (f.providerId = ".$userId." and f.status= ".USER_APPROVED.") or 
            f.requestId = ".$userId." ";


            $sqlmessage = "SELECT m.id, m.fromuid, m.touid, m.sendt, m.read, m.readt, m.messagetext, u.userid from messages m left join users u on u.Id =m.fromuid WHERE 'touid = ".$userId.' AND 'read' = 0 LIMIT 0, 30 ";


            if($result = $db->query($sql)){
                while($row = $db->fetchObject($result)){
                    $status="offline";
                    if(((int)$row->status)== USER_UNAPPROVED){
                        $status = "unapproved";
                    }else if(((int)$row->authenticateTimeDifference)< TIME_INTERVAL_FOR_USER_STATUS ){
                        $status = "online";
                    }
                    $out .="<friend username = '".$row->username."' status='".$status"' IP='".$row->IP."' userKey = '".$row->Id.'' port ='".$row->port."'/>";
                       
                    
                }
                    if($resultmessage = $db->query($sqlmessage)){
                        while($rowmessage =$db->fetchObject($resultmessage)){
                            $out .="<message from = '".$rowmessage->username."' send='".$rowmessage->sendt."'text ='".$rowmessage->messagetext."' />
                            
                            $sqlendmsg = "UPDATE messages SET 'read' = 1, 'readt' ='".DATE ("Y-m-d H:1")."' WHERE 'messages'.'id' = ".$rowmessage->id.";";
                            $db->query($sqlendmsg);
                        }
                    
                    }
                $out .= "</data>";
                
            }

        }
        break;
    case SignUpUser:
        if(isset($_REQUEST['email'])){
            $email=$_REQUEST['email'];
            $sql = "select Id from users where username = '".$username."' limit 1";
            
            if($result = $db->query($sql)){
                if($db->numRows($result)== 0){
                    $sql="insert into users(username, password, email) values ('".$username."', '".$password."', '".$email."') ";
                    
                    error_log("$sql",3,"error_log");
                    
                    if($db->query($sql)){
                        $out=SUCCESSFUL;
                    }else{
                        $out=FAILED
                    }
                }else{
                    $out = SIGN_UP_UsERNAME_CRASHED;
                }
            }
        
        }else{
            $out = FAILED;
        }
        break;

}
function authenticateUser($db, $username, $password){
    $sql22="select * from users where username ='".$username."' and password = '".$password."' limit 1";
    
    if($result22=$db->query($sql22)){
        if($row22=$db->fetchObject($result22)){
            $sql22="update users. IP = '".$_SERVER["REMOTE_ADDR]."' ,port=15145
            where Id = ".$row22->Id."
            limit 1";
            
            $db->query($sql22)
        
        }
    
    }
}

