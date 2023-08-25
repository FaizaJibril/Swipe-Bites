--getTransfersByUserId(long userId)
--select * from transfer
--SELECT * FROM transfer WHERE account_from = ? OR account_to = ?
select
		transfer_id
		,tt.transfer_type_desc
		,ts.transfer_status_desc
		,account_from
		,account_to
		,amount
		,ufrom.user_id as from_user_id
		,ufrom.username as from_user_name
		,uto.user_id as to_user_id
		,uto.username as to_user_name
	from
		transfer t
		join account ato
			on ato.account_id = t.account_to
		join tenmo_user uto
			on uto.user_id = ato.user_id
		join account afrom
			on afrom.account_id = t.account_from
		join tenmo_user ufrom
			on ufrom.user_id = afrom.user_id
		join transfer_type tt
			on tt.transfer_type_id = t.transfer_type_id		
		join transfer_status ts
			on ts.transfer_status_id = t.transfer_status_id
	where
		uto.user_id = 1001
		or
		ufrom.user_id=1001
		
		